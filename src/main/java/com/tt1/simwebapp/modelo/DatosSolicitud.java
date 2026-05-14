package com.tt1.simwebapp.modelo;

import java.util.Map;

public class DatosSolicitud
{
    private final Map<String, Integer> nums;

    public DatosSolicitud(Map<String, Integer> nums)
    {
        this.nums = nums;
    }

    public Map<String, Integer> getNums()
    {
        return nums;
    }
}
