package app.wqg.hookgame2;

import javax.microedition.khronos.opengles.GL10;

public interface GameState {
    void upDate(GL10 gl, int width, int height);
}
