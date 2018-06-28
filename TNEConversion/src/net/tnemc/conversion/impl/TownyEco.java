package net.tnemc.conversion.impl;

import com.github.tnerevival.core.db.sql.MySQL;
import com.github.tnerevival.core.db.sql.SQLite;
import net.tnemc.conversion.ConversionModule;
import net.tnemc.conversion.Converter;
import net.tnemc.conversion.InvalidDatabaseImport;
import net.tnemc.core.TNE;
import net.tnemc.core.common.api.IDFinder;
import net.tnemc.core.economy.currency.Currency;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.math.BigDecimal;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by Daniel on 5/28/2018.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
public class TownyEco extends Converter {
  private File configFile = new File("plugins/TownyEco/config.yml");
  private FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

  private String prefix = config.getString("database.table_prefix");
  @Override
  public String name() {
    return "TownyEco";
  }

  @Override
  public void mysql() throws InvalidDatabaseImport {
    db = new MySQL(conversionManager);
    String table = prefix + "balances";
    try {
      int index = mysqlDB().executeQuery("SELECT uuid, world, currency, balance FROM " + table + ";");

      while (mysqlDB().results(index).next()) {
        String uuid = mysqlDB().results(index).getString("uuid");
        String world = mysqlDB().results(index).getString("world");
        String currencyName = mysqlDB().results(index).getString("currency");
        String balance = mysqlDB().results(index).getString("balance");
        Currency currency = TNE.manager().currencyManager().get(world, currencyName);
        if(currency == null) {
          currency = TNE.manager().currencyManager().get(TNE.instance().defaultWorld);
        }

        ConversionModule.convertedAdd(IDFinder.getUsername(uuid), world, currency.name(), new BigDecimal(balance));
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sqlite() throws InvalidDatabaseImport {
    db = new SQLite(conversionManager);
    String table = prefix + "balances";
    try {
      int index = sqliteDB().executeQuery("SELECT uuid, world, currency, balance FROM " + table + ";");

      while (sqliteDB().results(index).next()) {
        String uuid = sqliteDB().results(index).getString("uuid");
        String world = sqliteDB().results(index).getString("world");
        String currencyName = sqliteDB().results(index).getString("currency");
        String balance = sqliteDB().results(index).getString("balance");
        Currency currency = TNE.manager().currencyManager().get(world, currencyName);
        if(currency == null) {
          currency = TNE.manager().currencyManager().get(TNE.instance().defaultWorld);
        }

        ConversionModule.convertedAdd(IDFinder.getUsername(uuid), world, currency.name(), new BigDecimal(balance));
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
