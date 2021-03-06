package org.granitemc.granite.api.config;

/*
 * License (MIT)
 *
 * Copyright (c) 2014. Granite Team
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

import java.util.*;

@SuppressWarnings("unchecked")
public class CfgList extends CfgCollection implements List<CfgValue> {
    List<CfgValue> value;

    public CfgList(List<CfgValue> list) {
        value = list;
    }

    public CfgList() {
        value = new ArrayList<>();
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return value.contains(o);
    }

    @Override
    public Iterator<CfgValue> iterator() {
        return value.iterator();
    }

    @Override
    public Object[] toArray() {
        return value.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return value.toArray(a);
    }

    @Override
    public boolean add(CfgValue cfgValue) {
        return value.add(cfgValue);
    }

    @Override
    public boolean remove(Object o) {
        return value.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return value.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends CfgValue> c) {
        return value.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends CfgValue> c) {
        return value.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return value.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return value.retainAll(c);
    }

    @Override
    public void clear() {
        value.clear();
    }

    @Override
    public CfgValue get(int index) {
        return value.get(index);
    }

    @Override
    public CfgValue set(int index, CfgValue element) {
        return value.set(index, element);
    }

    @Override
    public void add(int index, CfgValue element) {
        value.set(index, element);
    }

    @Override
    public CfgValue remove(int index) {
        return value.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return value.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return value.lastIndexOf(o);
    }

    @Override
    public ListIterator<CfgValue> listIterator() {
        return value.listIterator();
    }

    @Override
    public ListIterator<CfgValue> listIterator(int index) {
        return value.listIterator(index);
    }

    @Override
    public List<CfgValue> subList(int fromIndex, int toIndex) {
        return value.subList(fromIndex, toIndex);
    }

    @Override
    CfgValue getSegment(String name) {
        int i = Integer.parseInt(name);
        return value.get(i);
    }

    @Override
    void putSegment(String name, CfgValue value) {
        int i = Integer.parseInt(name);
        this.value.set(i, value);
    }

    @Override
    public Object unwrap() {
        List<Object> new_ = new ArrayList<>();
        for (CfgValue obj : value) {
            new_.add(obj.unwrap());
        }
        return new_;
    }

    @Override
    public CfgValueType getType() {
        return CfgValueType.LIST;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfgList cfgValues = (CfgList) o;

        if (!value.equals(cfgValues.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "CfgList{" +
                "value=" + value +
                '}';
    }
}
