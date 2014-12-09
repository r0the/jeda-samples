/*
 * Copyright (C) 2014 by Stefan Rothe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY); without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.jeda.tiled;

import ch.jeda.Data;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Image;

public final class TileLayer extends Layer {

    private final int height;
    private final Tile[] tiles;
    private final int width;

    public TileLayer(final Data properties, final String name, final double opacity, final boolean visible,
                     final int width, final int height, Tile[] tiles) {
        super(properties, name, opacity, visible);
        this.height = height;
        this.tiles = tiles;
        this.width = width;
    }

    @Override
    public TiledObject getObject(final String name) {
        return null;
    }

    Image getTileImage(final int x, final int y) {
        final int index = x + y * this.width;
        if (0 <= index && index < this.tiles.length && this.tiles[index] != null) {
            return this.tiles[index].getImage();
        }

        return null;
    }

    @Override
    public void draw(final Canvas canvas) {
        if (!this.isVisible()) {
            return;
        }

        final int alpha = (int) (this.getOpacity() * 255);
        final int tileHeight = this.getMap().getTileHeight();
        final int tileWidth = this.getMap().getTileWidth();
        int screenX = this.getX();
        int screenY = this.getY();
        int startX = 0;
        int startY = 0;
        int endX = this.width;
        int endY = this.height;
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                canvas.drawImage(screenX, screenY, this.getTileImage(x, y), alpha);
                screenY += tileHeight;
            }

            screenY = this.getY();
            screenX += tileWidth;
        }
    }
}