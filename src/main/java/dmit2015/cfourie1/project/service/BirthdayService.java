package dmit2015.cfourie1.project.service;

import common.jpa.AbstractJpaService;
import dmit2015.cfourie1.project.model.Birthday;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BirthdayService extends AbstractJpaService {

    @Transactional
    public void createBirthday(Birthday newBirthday) {
        super.create(newBirthday);
    }

    public Optional<Birthday> readOne(Long id) {
        return super.findOptional(Birthday.class, id);
    }

    public List<Birthday> readAll() {
        return super.findAll(Birthday.class);
    }

    @Transactional
    public Birthday updateBirthday(Birthday existingBirthday) {
        return super.save(existingBirthday);
    }

    @Transactional
    public void deleteBirthday(Long id) {
        super.deleteById(Birthday.class, id);
    }
}
