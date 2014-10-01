package org.granitemc.granite.chat;

/*****************************************************************************************
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
 ****************************************************************************************/

import org.granitemc.granite.api.chat.ChatComponent;
import org.granitemc.granite.api.chat.ChatComponentBuilder;

public class GraniteChatComponentBuilder implements ChatComponentBuilder {
    private ChatComponent component;

    public ChatComponentBuilder text(String text) {
        if (component == null) {
            component = new GraniteChatComponentText(text);
        } else {
            component = component.add(text);
        }
        return this;
    }

    @Override
    public ChatComponentBuilder translation(String key, Object... args) {
        ChatComponent component = new GraniteChatComponentTranslation(key, args);
        if (this.component == null) {
            this.component = component;
        } else {
            this.component = this.component.add(component);
        }
        return this;
    }

    public ChatComponentBuilder component(ChatComponent component) {
        if (this.component == null) {
            this.component = component;
        } else {
            this.component = this.component.add(component);
        }
        return this;
    }

    public ChatComponent build() {
        return component;
    }
}