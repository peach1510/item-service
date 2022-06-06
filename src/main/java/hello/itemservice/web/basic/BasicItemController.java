package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final이 붙은 걸로 생성자 자동 생성
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired     // 스프링에 생성자가 하나만 있으면 생략 가능하다
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    // 1. 프로퍼티 접근법으로 접근한다.
    // 2. 모델에 지정한 객체를 자동으로 넣어준다.
    public String addItemV2(@ModelAttribute("item") Item item,
                            Model model) {

//        Model Attribute가 자동으로 만들어 줌

//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);

        itemRepository.save(item);
//        model.addAttribute("item", item); 자동 추가되므로 생략 가능
        return "basic/item";
    }

//    @PostMapping("/add")
    // 3. 클래스 명을 첫 글자만 소문자로 바꿔서 model에 등록
    public String addItemV3(@ModelAttribute Item item) {

        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    // 4. ModelAttribute 생략 가능
    public String addItemV4(Item item) {

        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    // 4. ModelAttribute 생략 가능
    public String addItemV5(Item item) {

        itemRepository.save(item);
        // URL 인코딩이 필요
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    // 4. ModelAttribute 생략 가능
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        // Variable 값
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        // 쿼리 파라미터로 전달
        redirectAttributes.addAttribute("status", true);
        // URL 인코딩이 필요
        return "redirect:/basic/items/{itemId}";
    }
    
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    // 테스트형 데이터 생성
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
