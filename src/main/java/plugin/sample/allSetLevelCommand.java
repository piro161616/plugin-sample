package plugin.sample;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class allSetLevelCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player player) {
      player.sendMessage("実行できません。");
    } else {
      for(Player player : sender.getServer().getOnlinePlayers()){
        player.setLevel(Integer.parseInt(args[0]));
        System.out.println("All online players levels have been set to " + args[0] + ".");
      }
    }
    return false;
  }
}
