package JPABook.JPAShop.service;

import JPABook.JPAShop.controller.form.BookForm;
import JPABook.JPAShop.domain.Item.Book;
import JPABook.JPAShop.domain.Item.Item;
import JPABook.JPAShop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemService {

    // TODO: final 에 대한 필요성
    // 아래의 repo 변수 선언시 final 을 붙혀주는게 맞는데 학습이 부족해서
    // final 을 붙여야 하는 이유에 대한 공감을 못한 듯
    @Autowired
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) { this.itemRepository = itemRepository; }

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional
    public void update(BookForm bookForm) {
        Book book = (Book) itemRepository.findOne(bookForm.getId());

        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());
    }
}
