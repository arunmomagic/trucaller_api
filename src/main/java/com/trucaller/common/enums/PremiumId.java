package com.trucaller.common.enums;

import java.util.function.Function;

public enum PremiumId implements PersistableEnum<Integer> {
    momagic_1(1, "momagic_1"),
    momagic_3(3, "momagic_3"),
    momagic_7(7, "momagic_7"),
    momagic_15(15, "momagic_15"),
    momagic_30(30, "momagic_30");

    private final int value;
    private final String displayName;

    PremiumId(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }


    public static PremiumId getById(int id) {
        for(PremiumId e : values()) {
            if(e.value==id)
                return e;
        }
        return null;
    }

    private final static Function<String, PremiumId> func =
            EnumUtils.lookupMap(PremiumId.class, e -> e.name());

    public static PremiumId get(String str)
    {
        return func.apply(str);
    }

    public static String name(PremiumId deviceType) {
        return deviceType == null ? null : deviceType.name();
    }

    @Override
    public Integer getValue() {
        return value;
    }


    public String getDisplayName() {
        return displayName;
    }
}