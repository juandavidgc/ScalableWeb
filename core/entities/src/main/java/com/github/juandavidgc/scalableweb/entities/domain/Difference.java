package com.github.juandavidgc.scalableweb.entities.domain;

import lombok.Data;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Data
public class Difference {

    private int position;

    private int length;

    public Difference(int position, int length) {
        this.position = position;
        this.length = length;
    }
}
