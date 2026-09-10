package com.github.Citybuilder.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class InputEngine extends InputAdapter{

    private final OrthographicCamera camera;

    public InputEngine(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void handleInput() {

        float cameraSpeed = 525f;
        float deltaTime = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.camera.position.y += cameraSpeed * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.camera.position.y -= cameraSpeed * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.camera.position.x += cameraSpeed * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.camera.position.x -= cameraSpeed * deltaTime;
        }
        
        camera.update();
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        float zoomSpeed = 0.1f;

        camera.zoom += amountY * zoomSpeed;
        camera.zoom = MathUtils.clamp(camera.zoom, 0.4f, 8.0f);
        camera.update();

        return true;

    }
}