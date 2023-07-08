package net.exotia.plugins.exotiabackpack.configuration.providers.sections;

import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class LiteCommandsSection {
    public String noPermission =  "鼅 <white>Nie masz permisji do tej komendy! <gradient:#4fa943:#9ec52f><bold>({permissions})";

    public String invalidCommand = "鼅 <white>Nie poprawne użycie komendy: &7{command}";
    public String invalidCommandHeader = "鼅 <white>Nie poprawne użycie <gradient:#4fa943:#9ec52f><bold>komendy</bold></gradient>&f!";
    public String helperLine = " <white>» <gray>{schema}";

    public String playerIsOffline = "鼅 <white>Ten gracz jest offline!";
    public String commandOnlyForPlayer = "鼅 <white>Tej komendy nie mozesz <gradient:#4fa943:#9ec52f><bold>wywołać</bold></gradient> z poziomu konsoli!";
}
