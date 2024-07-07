package plugin.sample;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Armadillo;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ChiseledBookshelfInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Sample extends JavaPlugin implements Listener {

  private BigInteger count = BigInteger.ZERO;

  @Override
  public void onEnable() {
    saveDefaultConfig();

    Bukkit.getPluginManager().registerEvents(this, this);
    getCommand("setLevel").setExecutor(new SetLevelCommand(this));
    getCommand("allSetLevel").setExecutor(new allSetLevelCommand());
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e){
    Player player = e.getPlayer();
    World world = player.getWorld();
    Location playerLocation = player.getLocation();

    world.spawn(new Location(world,playerLocation.getX() + 3 , playerLocation.getY() + 1  , playerLocation.getZ() + 1 ), Armadillo.class);
  }

  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   *
   * @param e イベント
   */

  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) throws IOException {
    // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.getPlayer();
    World world = player.getWorld();

    List<Color> colorList = List.of(Color.WHITE, Color.ORANGE, Color.ORANGE, Color.NAVY);
    if (count.isProbablePrime(1)) {
      for (Color color : colorList) {
        // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // 花火オブジェクトが持つメタ情報を取得。
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // メタ情報に対して設定を追加したり、値の上書きを行う。
        // 今回は青色で星型の花火を打ち上げる。
        fireworkMeta.addEffect(
            FireworkEffect.builder()
                .withColor(color)
                .with(Type.STAR)
                .withFlicker()
                .build());
        fireworkMeta.setPower(1);

        // 追加した情報で再設定する。
        firework.setFireworkMeta(fireworkMeta);
      }
    }
    count = count.add(BigInteger.ONE);
  }

  @EventHandler
  public void onPlayerBedEnter(PlayerBedEnterEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStacks = player.getInventory().getContents();
    Arrays.stream(itemStacks)
        .filter(
            item -> !Objects.isNull(item) && item.getMaxStackSize() == 64 && item.getAmount() < 64)
        .forEach(itemStack -> itemStack.setAmount(0));

    player.getInventory().setContents(itemStacks);
  }

  @EventHandler
  public void setJoinMassage(PlayerJoinEvent e) {
    // Joinしたときにメッセージを表示
    e.setJoinMessage(e.getPlayer().getDisplayName() + "さん ようこそ！");
  }
}

