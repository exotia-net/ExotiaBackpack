package net.exotia.plugins.exotiabackpack.configuration.providers.sections;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Contextual
public class DatabaseSection {
    @Description({"# MySQL database hostname or ip address"})
    public String hostname = "10.10.10.2";
    @Description({"# MySQL port. default 3306"})
    public int port = 3306;
    @Description({"# MySQL username"})
    public String username = "exotia_admin";
    @Description({"# MySQL user password"})
    public String password = "q5KBEHn!8IhbQUCJ";
    @Description({"# MySQL database name"})
    public String database = "exotia_survival";
}
