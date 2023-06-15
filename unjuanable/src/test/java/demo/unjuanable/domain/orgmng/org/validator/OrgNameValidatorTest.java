package demo.unjuanable.domain.orgmng.org.validator;

import demo.unjuanable.common.framework.exception.BusinessException;
import demo.unjuanable.domain.orgmng.org.OrgRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrgNameValidatorTest {

    static final String DEFAULT_EMP_NAME = "王教头";
    static final long DEFAULT_TENANT_ID = 1L;
    static final long DEFAULT_ORG_ID = 1L;

    @Mock
    private OrgRepository orgRepository;

    @InjectMocks
    private OrgNameValidator validator;

    @Test
    public void nameShouldNotDuplicatedInSameSuperior_shouldThrowException_whenDuplicated() {
        setupExists(true);

        assertThrows(BusinessException.class,
                () -> validator.shouldNotDuplicatedInSameSuperior(
                        DEFAULT_TENANT_ID
                        , DEFAULT_ORG_ID
                        , DEFAULT_EMP_NAME));

        verifyCallExistsBySuperiorIdAndName();
    }

    @Test
    public void nameShouldNotDuplicatedInSameSuperior_shouldNotThrowException_whenNotDuplicated() {
        setupExists(false);

        validator.shouldNotDuplicatedInSameSuperior(
                DEFAULT_TENANT_ID
                , DEFAULT_ORG_ID
                , DEFAULT_EMP_NAME);

        verifyCallExistsBySuperiorIdAndName();
    }

    private void setupExists(boolean exists) {
        when(orgRepository.existsBySuperiorIdAndName(
                DEFAULT_TENANT_ID
                , DEFAULT_ORG_ID
                , DEFAULT_EMP_NAME))
                .thenReturn(exists);
    }

    private void verifyCallExistsBySuperiorIdAndName() {
        verify(orgRepository, times(1)).existsBySuperiorIdAndName(
                DEFAULT_TENANT_ID
                , DEFAULT_ORG_ID
                , DEFAULT_EMP_NAME);
    }


}