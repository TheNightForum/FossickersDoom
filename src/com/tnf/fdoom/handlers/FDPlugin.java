package com.tnf.fdoom.handlers;

import com.tnf.fdoom.Game;
import net.xeoh.plugins.base.Plugin;

/**
 * Created by brayden on 14/6/17.
 */
public interface FDPlugin extends Plugin{

    int g = 4;

    void onLoad(Game game);

    public default void ocws()
    {

    }
}
