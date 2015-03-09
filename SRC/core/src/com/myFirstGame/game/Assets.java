package com.myFirstGame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by chris on 3/1/15.
 */
public class Assets {
    public static Array<Texture> texturesArray;
    public static Array<TextureRegion> textureRegionsArray;
    public static Array<Sprite> spritesArray;
    public static Array<Animation> animationArray;

    public static Texture imgTexture;
    public static Sprite imgSprite;

    public static void initialize(){
        Assets.animationArray = new Array<Animation>();
        Assets.texturesArray = new Array<Texture>();
        Assets.textureRegionsArray = new Array<TextureRegion>();
        Assets.spritesArray = new Array<Sprite>();
    }

    public static void loadRegion(String path, int width, int height){
        Texture t = new Texture(Gdx.files.internal(path));
        Assets.texturesArray.add(t);
        //Sprite.split()
        //Assets.texturesArray.add(new TextureRegion(t, width, height));
        /*
            FUCK!  there is literally an AssetsManager class.  this whole class is useless.
            I will repurpose it to use it later.
         */
    }

    public static void loadTest(){
        //Assets.imgTexture = new Texture(Gdx.files.local("badlogic.jpg"));
        //Assets.imgSprite = new Sprite(Assets.imgTexture);
    }

}
