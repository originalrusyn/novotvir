package admin.user.service;

import admin.user.dto.CriteriaSuggestionsDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static admin.user.service.UserServiceImpl.aliasClassMap;

// @author: Mykhaylo Titov on 29.08.14 22:36.
@Service
public class UserSearchSuggestionServiceImpl {

    @PersistenceContext EntityManager entityManager;

    @Cacheable("criteriaSuggestionsDTO")
    public CriteriaSuggestionsDTO getCriteriaSuggestionsDTO() {

        Metamodel metaModel = entityManager.getMetamodel();

        List<String> suggestions = new ArrayList<>();

        for (Entry<String, Class<?>> aliasClassEntry : aliasClassMap.entrySet()) {
            ManagedType<?> managedType = metaModel.managedType(aliasClassEntry.getValue());
            suggestions.addAll(managedType.getAttributes().stream().map(attribute -> aliasClassEntry.getKey() + "." + attribute.getName()).collect(Collectors.toList()));
        }

        return new CriteriaSuggestionsDTO().setSuggestions(suggestions);
    }
}
