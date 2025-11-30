package synergy.botikspring.service;

import synergy.botikspring.dto.ContactDto;
import java.util.List;

public interface ContactService {
    List<ContactDto> findAll();
    ContactDto findById(Long id) throws Exception;
    ContactDto create(ContactDto dto) throws Exception;
    ContactDto update(ContactDto dto, Long id) throws Exception;
    void delete(Long id);
}
