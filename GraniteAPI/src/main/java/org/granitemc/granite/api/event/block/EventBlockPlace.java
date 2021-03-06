package org.granitemc.granite.api.event.block;

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

import org.granitemc.granite.api.block.Block;
import org.granitemc.granite.api.block.BlockType;
import org.granitemc.granite.api.entity.player.Player;
import org.granitemc.granite.api.event.Event;

public class EventBlockPlace extends Event {
    Block block;
    Player player;

    BlockType newBlockType;
    BlockType oldBlockType;

    public EventBlockPlace(Block block, Player player, BlockType newBlockType, BlockType oldBlockType) {
        this.block = block;
        this.player = player;
        this.newBlockType = newBlockType;
        this.oldBlockType = oldBlockType;
    }

    /**
     * Returns the block that was placed
     */
    public Block getBlock() {
        return block;
    }

    /**
     * Returns the player that placed the block
     */
    public Player getPlayer() {
        return player;
    }

    public BlockType getNewBlockType() {
        return newBlockType;
    }

    public BlockType getOldBlockType() {
        return oldBlockType;
    }

    public void setNewBlockType(BlockType newBlockType) {
        this.newBlockType = newBlockType;
    }
}
