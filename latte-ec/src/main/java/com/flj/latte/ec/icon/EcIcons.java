package com.flj.latte.ec.icon;

import com.joanzapata.iconify.Icon;



public enum EcIcons implements Icon {
    icon_scan('\ue638'),
    icon_ali_pay('\ue662'),
    icon_qrcode('\ue639');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
