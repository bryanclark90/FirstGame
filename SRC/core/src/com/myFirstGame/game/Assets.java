package com.myFirstGame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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
    public static Array<Sprite> spritesArray;
    public static Array<Animation> animationArray;

    public static AssetManager am;
    public static float effectsVol = 0.5f;
    public static float musicVol = 0.5f;
    public static Texture imgTexture;
    public static Sprite imgSprite;

    public static void initialize(){
        Assets.am = new AssetManager();
        Assets.animationArray = new Array<Animation>();
        Assets.texturesArray = new Array<Texture>();
        Assets.soundsArray = new Array<Sound>();
        Assets.spritesArray = new Array<Sprite>();
    }

    public static void loadAnimation(String path, int width, int height, float frameLength){
        Texture t = new Texture(Gdx.files.internal(path));
        Assets.texturesArray.add(t);
        TextureRegion tr[][] = Sprite.split(t,width,height);
        for(int i = 0; i < tr.length; i++) {
            Assets.animationArray.add(new Animation(frameLength, tr[i]));
        }

    }

    public static void loadSprite(String path, int width, int height){
        Texture t = new Texture(Gdx.files.internal(path));
        Assets.texturesArray.add(t);
    }

    public static void load(){
        Assets.initialize();
        Assets.loadAnimation("StandingFixed.png", 32, 32, 1/2f);
        Assets.loadAnimation("Walking_Fatman.png", 32, 32, 1/12f);
        Assets.loadAnimation("Hurt_Spritesheet.png", 32, 32, 1/20f);
        Assets.loadAnimation("Spitting_Headsonly.png", 32, 32, 1/8f);
        Assets.loadAnimation("Celebration.png", 32, 32, 1/6f);
        Assets.loadAnimation("DeathSprite.png", 32, 32, 1/6f);
        Assets.loadAnimation("burger.png", 32, 32, 1/12f);
        Assets.loadAnimation("burger.png", 32, 32, 1/12f);
        Assets.loadAnimation("CrackBackground.png",320,180, 1/2);
        loadSound();
    }

    public static void loadTest(){
        Assets.imgTexture = new Texture(Gdx.files.local("badlogic.jpg"));
        //Assets.imgSprite = new Sprite(Assets.imgTexture);
    }

    public static Array<Sound> soundsArray;

    public static void loadSound(){
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("BurgerThud.wav")));
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("ShootingBurger.wav")));
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("glitch.wav")));
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("Hurt1.wav")));//3
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("Hurt2.wav")));
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("Hurt3.wav")));
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("Hurt4.wav"))); //6
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("LosingSong.wav")));
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("WinningSong.wav")));
        Assets.soundsArray.add(Gdx.audio.newSound(Gdx.files.internal("BurgerTheme.wav"))); //9
    }

}
