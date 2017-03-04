package com.tnf.fdoom.screen;

import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.gfx.Font;
import com.tnf.fdoom.gfx.Screen;
import com.tnf.fdoom.handlers.Data;
import com.tnf.fdoom.handlers.Handler;
import com.tnf.fdoom.sound.Sound;

import java.io.File;

public class NewPlayer extends Menu {
    private String name = "";
    private static String Header = "Username.";
    private static boolean running = true;
    private int wncol = Color.get(-1, 0, Color.rgb(0, 0, 0), Color.rgb(255, 0, 0));


    public void tick() {
    	if (running){
    		this.typecode();
    		if (input.enter.clicked){
                Handler.writeConfig(Handler.PlayerName, name);
    			this.name = "";
    		}
        if (input.escape.clicked){
          game.setMenu(new TitleMenu());
        }
    	}
    }

    public void render(Screen screen) {
        screen.clear(0);

        final int col2 = Color.get(-1, 555, 555, 555);
        Font.draw(Header, screen, this.centertext(Header), 20, Color.get(-1, 500, 500, 500));
        Font.draw(this.name, screen, this.centertext(this.name), 50, this.wncol);
        Font.draw("A-Z, 0-9, 36 Characters", screen, this.centertext("A-Z, 0-9, 36 Characters"), 80, col2);
        Font.draw("(Backspace as well)", screen, this.centertext("(Backspace as well)"), 92, col2);

        Font.draw("Press Enter to create", screen, this.centertext("Press Enter to create"), 162, col2);
        Font.draw("Press Esc to cancel", screen, this.centertext("Press Esc to cancel"), 172, col2);
    }

    public void typecode() {

        if (this.input.backspace.clicked && this.name.length() > 0) {
            this.name = this.name.substring(0, this.name.length() - 1);
        }
        if (this.name.length() < 36) {
            if (this.input.a0.clicked) {
                this.name = String.valueOf(this.name) + "0";
            }
            if (this.input.a1.clicked) {
                this.name = String.valueOf(this.name) + "1";
            }
            if (this.input.a2.clicked) {
                this.name = String.valueOf(this.name) + "2";
            }
            if (this.input.a3.clicked) {
                this.name = String.valueOf(this.name) + "3";
            }
            if (this.input.a4.clicked) {
                this.name = String.valueOf(this.name) + "4";
            }
            if (this.input.a5.clicked) {
                this.name = String.valueOf(this.name) + "5";
            }
            if (this.input.a6.clicked) {
                this.name = String.valueOf(this.name) + "6";
            }
            if (this.input.a7.clicked) {
                this.name = String.valueOf(this.name) + "7";
            }
            if (this.input.a8.clicked) {
                this.name = String.valueOf(this.name) + "8";
            }
            if (this.input.a9.clicked) {
                this.name = String.valueOf(this.name) + "9";
            }
            if (this.input.a.clicked) {
                this.name = String.valueOf(this.name) + "a";
            }
            if (this.input.b.clicked) {
                this.name = String.valueOf(this.name) + "b";
            }
            if (this.input.c.clicked) {
                this.name = String.valueOf(this.name) + "c";
            }
            if (this.input.d.clicked) {
                this.name = String.valueOf(this.name) + "d";
            }
            if (this.input.e.clicked) {
                this.name = String.valueOf(this.name) + "e";
            }
            if (this.input.f.clicked) {
                this.name = String.valueOf(this.name) + "f";
            }
            if (this.input.g.clicked) {
                this.name = String.valueOf(this.name) + "g";
            }
            if (this.input.h.clicked) {
                this.name = String.valueOf(this.name) + "h";
            }
            if (this.input.i.clicked) {
                this.name = String.valueOf(this.name) + "i";
            }
            if (this.input.j.clicked) {
                this.name = String.valueOf(this.name) + "j";
            }
            if (this.input.k.clicked) {
                this.name = String.valueOf(this.name) + "k";
            }
            if (this.input.l.clicked) {
                this.name = String.valueOf(this.name) + "l";
            }
            if (this.input.m.clicked) {
                this.name = String.valueOf(this.name) + "m";
            }
            if (this.input.n.clicked) {
                this.name = String.valueOf(this.name) + "n";
            }
            if (this.input.o.clicked) {
                this.name = String.valueOf(this.name) + "o";
            }
            if (this.input.p.clicked) {
                this.name = String.valueOf(this.name) + "p";
            }
            if (this.input.q.clicked) {
                this.name = String.valueOf(this.name) + "q";
            }
            if (this.input.r.clicked) {
                this.name = String.valueOf(this.name) + "r";
            }
            if (this.input.s.clicked) {
                this.name = String.valueOf(this.name) + "s";
            }
            if (this.input.t.clicked) {
                this.name = String.valueOf(this.name) + "t";
            }
            if (this.input.u.clicked) {
                this.name = String.valueOf(this.name) + "u";
            }
            if (this.input.v.clicked) {
                this.name = String.valueOf(this.name) + "v";
            }
            if (this.input.w.clicked) {
                this.name = String.valueOf(this.name) + "w";
            }
            if (this.input.x.clicked) {
                this.name = String.valueOf(this.name) + "x";
            }
            if (this.input.y.clicked) {
                this.name = String.valueOf(this.name) + "y";
            }
            if (this.input.z.clicked) {
                this.name = String.valueOf(this.name) + "z";
            }
            if (this.input.space.clicked) {
                this.name = String.valueOf(this.name) + " ";
            }
        }
    }
}
