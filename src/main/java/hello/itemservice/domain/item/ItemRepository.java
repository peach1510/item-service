package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 컴포넌트 스캔의 대상이 된다.
@Repository
public class ItemRepository {

    // static 사용, 동시에 여러 스레드가 접근할 수 있는 변수일 경우 ConcurrentHashMap 사용해야 한다.
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 클래스 커지면 이렇게 일부 요소만 변경하는 것보다는 DTO 클래스 하나 새로 생성해서 명확하게 관리하는 게 나음.
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    // 해시맵 다 날림
    public void clearStore() {
        store.clear();
    }
}
