package com.diabin.fastec.example.generators;

import com.flj.latte.annotations.EntryGenerator;
import com.flj.latte.wechat.templates.WXEntryTemplate;



@SuppressWarnings("unused")
@EntryGenerator(
        packageName = "com.diabin.fastec.example",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
