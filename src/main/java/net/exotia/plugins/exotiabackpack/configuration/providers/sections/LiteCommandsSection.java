package net.exotia.plugins.exotiabackpack.configuration.providers.sections;

import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class LiteCommandsSection {
    public String noPermission = "&8&l>> &cNie masz permisji do tej komendy! &7({permissions})";

    public String invalidCommand = "<red>Nie poprawne użycie komendy &8>> &7{command}";
    public String invalidCommandHeader = "&cNie poprawne użycie komendy!";
    public String helperLine = "&8 >> &7{schema}";

    public String playerIsOffline = "&8&l>> &cTen gracz jest offline!";
    public String commandOnlyForPlayer = "&8&l>> &cTej komendy nie mozesz wywolac z poziomu konsoli!";
}
