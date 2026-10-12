# Sausage Craft

**NeoForge 1.21.1 · Java 21**

Сосиски в тесте как еда и декор: *Классическая (гусеничка)*, *Косичка* и три новых варианта косичек, плюс **Сосисочный верстак**.
Sausages in dough as food and table decor: *Classic*, *Braided*, three new braid variants, plus the **Sausage Workstation**.

## Содержимое / Content

| Предмет | Тип |
|---|---|
| Классическая сосиска в тесте / Classic Sausage in Dough | еда (6 голода, 0.6 насыщение) + декор |
| Сосиска в тесте «Косичка» / Braided Sausage in Dough | еда (6 голода, 0.6 насыщение) + декор |
| Золотая косичка / Golden Sausage Braid | еда + декор |
| Румяная косичка / Ruddy Sausage Braid | еда + декор |
| Пышная косичка / Fluffy Sausage Braid | еда + декор |
| Сосиска в тесте «Гусеница» / Caterpillar Sausage in Dough | еда + декор (детальная модель) |
| Сосисочный верстак / Sausage Workstation | станция крафта |

- Еда: ПКМ с зажатием / eat by holding right-click.
- Декор: ставится на твёрдую поверхность, поворачивается по игроку (facing), поддерживает waterlogged.
- **Все предметы — полноценные 3D-модели** в инвентаре, в руке и в мире (плоских иконок больше нет).
- **Сосисочный верстак**: ПКМ по блоку открывает GUI в стиле камнереза — положите любое сырое мясо (говядина, свинина, курица, баранина, кролчатина) и выберите любую сосиску (1 мясо = 1 сосиска).
- Своя вкладка «Сосисочная» / «Sausage Craft» в креативе.

## Сборка / Build

```bash
./gradlew build
```

Готовый jar: `build/libs/sausage_craft-1.1.0.jar`. CI: GitHub Actions собирает мод на каждый push.
