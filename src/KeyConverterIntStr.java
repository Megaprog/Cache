/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

import cache.store.KeyConverter;

public class KeyConverterIntStr implements KeyConverter<Integer, String> {

    @Override
    public String keyTo(Integer key) {
        return key.toString();
    }

    @Override
    public Integer keyFrom(String some) {
        return Integer.valueOf(some);
    }
}
