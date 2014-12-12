/*
 * License (MIT)
 *
 * Copyright (c) 2014 Granite Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.granitepowered.granite.mixin;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.reflect.ClassPath;
import javassist.*;
import org.apache.commons.lang3.ArrayUtils;
import org.granitepowered.granite.mappings.Mappings;

import java.io.IOException;
import java.util.Map;

public class MixinManager {
    CodeConverter converter;

    //Set<CtClass> mixinClasses;
    BiMap<CtClass, CtClass> mixinToMinecraftMap;

    public void inject() {
        //mixinClasses = new HashSet<>();
        mixinToMinecraftMap = HashBiMap.create();

        converter = new CodeConverter();
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses()) {
                CtClass clazz = ClassPool.getDefault().get(info.getName());
                if (clazz.hasAnnotation(Mixin.class)) {
                    //mixinClasses.add(clazz);

                    Mixin annotation = (Mixin) clazz.getAnnotation(Mixin.class);
                    CtClass mcClazz = Mappings.getCtClass(annotation.clazz());
                    mixinToMinecraftMap.put(clazz, mcClazz);
                }
            }

            for (Map.Entry<CtClass, CtClass> clazz : mixinToMinecraftMap.entrySet()) {
                preInject(clazz.getKey(), clazz.getValue());
            }

            for (Map.Entry<CtClass, CtClass> clazz : mixinToMinecraftMap.entrySet()) {
                inject(clazz.getKey(), clazz.getValue());
            }

            for (CtClass mcClazz : mixinToMinecraftMap.values()) {
                mcClazz.toClass();
            }
        } catch (IOException | NotFoundException | ClassNotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }

    public void preInject(CtClass clazz, CtClass mcClazz) {
        try {
            for (CtField field : clazz.getFields()) {
                /*if (mixinToMinecraftMap.containsKey(field.getType())) {
                    field.setType(mixinToMinecraftMap.get(field.getType()));
                }*/

                if (field.hasAnnotation(Shadow.class)) {
                    converter.redirectFieldAccess(field, mcClazz, Mappings.getCtField(mcClazz, field.getName()).getName());
                }
            }

            for (CtMethod method : clazz.getDeclaredMethods()) {
                if (method.hasAnnotation(Method.class)) {
                    Method annotation = (Method) method.getAnnotation(Method.class);

                    CtMethod mcMethod = Mappings.getCtMethod(mcClazz, method.getName());
                    converter.redirectMethodCall(method, mcMethod);
                }
            }
        } catch (CannotCompileException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void inject(CtClass clazz, CtClass mcClazz) {
        try {
            for (CtMethod method : clazz.getDeclaredMethods()) {
                if (method.hasAnnotation(Method.class)) {
                    Method annotation = (Method) method.getAnnotation(Method.class);

                    CtMethod mcMethod = Mappings.getCtMethod(mcClazz, method.getName());

                    String oldName = mcMethod.getName();
                    mcMethod.setName(mcMethod.getName() + "$mixin");

                    CtMethod newMethod = new CtMethod(mcMethod.getReturnType(), oldName, mcMethod.getParameterTypes(), mcMethod.getDeclaringClass());
                    newMethod.setBody(method, new ClassMap());

                    String fieldName = "mixin$retval$" + oldName;

                    if (newMethod.getReturnType() != CtClass.voidType) {
                        mcClazz.addField(new CtField(mcMethod.getReturnType(), "mixin$retval$" + oldName, mcClazz));
                    }

                    switch (annotation.applyMode()) {
                        case BEFORE:
                            newMethod.insertAfter("return " + mcMethod.getName() + "($$);");
                        case AFTER:
                            if (newMethod.getReturnType() != CtClass.voidType) {
                                newMethod.insertBefore(fieldName + " = " + mcMethod.getName() + "($$);");
                                newMethod.insertAfter("return " + fieldName + ";");
                            } else {
                                newMethod.insertBefore(mcMethod.getName() + "($$);");
                            }
                    }

                    mcClazz.addMethod(newMethod);
                }
                /*CtMethod mcMethod = new CtMethod(
                        toMinecraftClass(method.getReturnType());
                );

                if (method.hasAnnotation(Overwrite.class)) {
                    mcMethod = new CtMethod(method, mcClazz, new ClassMap());
                } else if (method.hasAnnotation(Before.class)) {
                    mcMethod = new CtMethod()
                }


                mcMethod.instrument(converter);
                mcClazz.addMethod(mcMethod);*/
            }

            mcClazz.setInterfaces(ArrayUtils.addAll(mcClazz.getInterfaces(), clazz.getInterfaces()));
        } catch (CannotCompileException | NotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public CtClass toMinecraftClass(CtClass mixinClass) {
        if (mixinToMinecraftMap.containsKey(mixinClass)) return mixinToMinecraftMap.get(mixinClass);
        return mixinToMinecraftMap.get(mixinClass);
    }
}
