package com.spaceshooter;

import org.junit.Test;

public class AsteroidTest {

    @Test
    public void getAngle( ) {
            for(int i = 0; i< 20 ; i++){
                double angle = Asteroid.getAngle( );
                System.out.print(angle);
                System.out.println("sine-->" + Math.sin( angle ));

                double angle2 = Math.toDegrees(Math.random() * 180);
                System.out.print(angle2);
                System.out.println("sine2-->" + Math.sin( angle2 ));

            }
    }
}