package novotvir.service.impl;

import novotvir.dto.CriteriaSuggestionsDTO;
import novotvir.service.UserSearchSuggestionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static novotvir.service.impl.UsersServiceImpl.aliasClassMap;

// @author: Mykaylo Titov on 29.08.14 22:36.
@Service
public class UserSearchSuggestionServiceImpl implements UserSearchSuggestionService {

    @PersistenceContext EntityManager entityManager;

    @Override
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
