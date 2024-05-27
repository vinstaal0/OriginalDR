package nl.vinstaal0.Dungeonrealms.Monstermechanics;

import com.mojang.authlib.GameProfile;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Random;
import java.util.UUID;

public enum MobType {

    ACOLYTE(Material.SKULL_ITEM, (short) 3, "acolyte", EntityType.SKELETON, true,false,"InfinityWarrior_"),
    BANDIT(Material.SKULL_ITEM, (short) 3, "bandit", EntityType.SKELETON, true, false,"Xmattpt", "TheNextPaladin"),
    BLAZE(Material.SKULL_ITEM, (short) 3, "blaze", EntityType.BLAZE, true, false, "MHF_Blaze"),
    CAVE_SPIDER(Material.SKULL_ITEM, (short) 3, "spider2", EntityType.CAVE_SPIDER, true, false, "MHF_CaveSpider"),
    DAEMON(Material.SKULL_ITEM, (short) 3, "daemon", EntityType.SKELETON, true, false, "daemon" ),
    GOBLIN(Material.SKULL_ITEM, (short) 3, "goblin", EntityType.ZOMBIE, true, false,"dEr_t0d", "niv330"),
    IMP(Material.SKULL_ITEM, (short) 3, "imp", EntityType.PIG_ZOMBIE,  true, true, "MHF_PigZombie"),
    IRON_GOLEM(Material.SKULL_ITEM, (short) 3, "golem", EntityType.IRON_GOLEM, true, false, "MHF_Golem"),
    LIZARDMAN(Material.SKULL_ITEM, (short) 3, "lizardman", EntityType.ZOMBIE, true,false,"_Kashi_"),
    MAGMA_CUBE(Material.SKULL_ITEM, (short) 3, "magmacube", EntityType.MAGMA_CUBE, true,false, "MagmaCube"),
    MONK(Material.SKULL_ITEM, (short) 3, "monk", EntityType.ZOMBIE, true,false,"Yhmen"),
    NAGA(Material.SKULL_ITEM, (short) 3, "naga", EntityType.ZOMBIE, true,false,"Das_Doktor"),
    PLAYER(Material.SKULL_ITEM, (short )3, "", EntityType.PLAYER, false,false),
    SKELETON(Material.SKULL_ITEM, (short) 0, "skeleton", EntityType.SKELETON, true,false),
    SLIME(Material.SKULL_ITEM, (short) 3, "slime", EntityType.SLIME, false, false, "MHF_Slime"),
    SPIDER(Material.SKULL_ITEM, (short) 3, "spider", EntityType.SPIDER, true, false, "MHF_SPIDER"),
    TRIPOLI_SOLDIER(Material.SKULL_ITEM, (short) 3, "tripoli1", EntityType.SKELETON, true,false,"Xmattpt"),
    TROLL(Material.SKULL_ITEM, (short) 3, "troll1", EntityType.ZOMBIE, true, false,"ArcadiaMovies", "Malware"),
    WITCH(Material.SKULL_ITEM, (short) 3, "witch", EntityType.WITCH, true,false, "Which"),
    WITHER_SKELETON(Material.SKULL_ITEM, (short) 1,  "skeleton2", EntityType.SKELETON, false, false), // TODO update once updating to new MC version
    WOLF(Material.SKULL_ITEM, (short) 3, "wolf", EntityType.WOLF, true, false, "Wolf"),
    ZOMBIE(Material.SKULL_ITEM, (short) 2, "zombie", EntityType.ZOMBIE, true, false);

    final Material material;
    final short data;
    final String legacyType;
    final EntityType entityType;
    final boolean spawnable;
    final boolean baby;
    final String[] owners;

    MobType(Material material, short data, String legacyType, EntityType entityType, boolean spawnable, boolean baby, String... owners) {
        this.material = material;
        this.data = data;
        this.legacyType = legacyType;
        this.entityType = entityType;
        this.spawnable = spawnable;
        this.baby = baby;
        this.owners = owners;
    }

    public ItemStack createItemStack(String customName) {
        ItemStack itemStack = new ItemStack(this.material, 1, data);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        if (owners.length == 0) {
            skullMeta.setDisplayName(customName);

            itemStack.setItemMeta(skullMeta);

            return itemStack;

        }

        if (owners.length > 1) {
            Random random = new Random();
            int randomIndex = random.nextInt(owners.length);

            skullMeta.setOwner(owners[randomIndex]);
            skullMeta.setDisplayName(customName);

            itemStack.setItemMeta(skullMeta);

            return itemStack;

        }

        skullMeta.setOwner(owners[0]);
        skullMeta.setDisplayName(customName);

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }
}