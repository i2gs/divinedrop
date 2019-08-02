package com.demkom58.divinedrop.versions.V14R1;

import com.demkom58.divinedrop.ConfigurationData;
import com.demkom58.divinedrop.DivineDrop;
import com.demkom58.divinedrop.ItemsHandler;
import com.demkom58.divinedrop.lang.Language;
import com.demkom58.divinedrop.versions.V11R1.V11R1;
import com.demkom58.divinedrop.versions.V12R1.V12Listener;
import com.demkom58.divinedrop.versions.V13R1.V13LangParser;
import com.demkom58.divinedrop.versions.Version;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class V14R1 implements Version {
    public static final String VERSION = "1.14";
    public static final String PATH = "minecraft/lang/%s.json";

    private final DivineDrop plugin;
    private final ConfigurationData data;
    private final ItemsHandler logic;

    private V14R1() {
        this.plugin = null;
        this.data = null;
        this.logic = null;
    }

    public V14R1(@NotNull final DivineDrop plugin,
                 @NotNull final ConfigurationData data,
                 @NotNull final ItemsHandler logic) {
        this.plugin = plugin;
        this.data = data;
        this.logic = logic;
    }

    @Override
    @Nullable
    public String getI18NDisplayName(@Nullable ItemStack item) {
        if (item == null)
            return null;

        return getName(item);
    }

    @NotNull
    @Override
    public String getLangPath(@NotNull final String locale) {
        return String.format(PATH, locale.toLowerCase());
    }

    @NotNull
    @Override
    public Map<String, String> parseLang(@NotNull InputStream inputStream) throws IOException {
        return V13LangParser.parseLang(inputStream);
    }

    @NotNull
    @Override
    public String id() {
        return VERSION;
    }

    @NotNull
    @Override
    public Listener getListener() {
        return new V12Listener(plugin, data, logic);
    }

    @NotNull
    @Override
    public String reformatLangCode(@NotNull final String localeCode) {
        return V11R1.langCode(localeCode);
    }

    @Nullable
    private String getName(ItemStack bItemStack) {
        final net.minecraft.server.v1_14_R1.ItemStack itemStack = org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack.asNMSCopy(bItemStack);
        final net.minecraft.server.v1_14_R1.NBTTagCompound nbtTagCompound = itemStack.b("display");

        if (nbtTagCompound != null) {
            if (nbtTagCompound.hasKeyOfType("Name", 8)) {
                final net.minecraft.server.v1_14_R1.IChatBaseComponent name =
                        net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer.a(nbtTagCompound.getString("Name"));
                return name == null ? null : name.getString();
            }
        }

        return getLangNameNMS(itemStack);
    }

    private String getLangNameNMS(net.minecraft.server.v1_14_R1.ItemStack itemStack) {
        return Language.getInstance().getLocName(itemStack.getItem().getName()).trim();
    }

}
