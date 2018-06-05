package com.diabin.fastec.example.generators;

import com.flj.latte.annotations.AppRegisterGenerator;
import com.flj.latte.wechat.templates.AppRegisterTemplate;


@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.diabin.fastec.example",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
