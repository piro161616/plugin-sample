package plugin.sample;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLevelCommand implements CommandExecutor {

  private Sample sample;

  public SetLevelCommand(Sample sample) {
    this.sample = sample;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player player) {
      if (args.length == 1) {
        player.setLevel(Integer.parseInt(args[0]));
      } else
        player.sendMessage(sample.getConfig().getString("Message"));
      }
      return false;
    }
}