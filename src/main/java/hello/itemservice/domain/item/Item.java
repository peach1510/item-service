package hello.itemservice.domain.item;

import lombok.Data;

// @Data는 핵심 로직에서 이상하게 동작할 수 있기 때문에 위험!
// @Getter @Setter
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
