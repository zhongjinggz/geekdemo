package demo.unjuanable.domain.common.validator;

import demo.unjuanable.common.framework.exception.BusinessException;
import demo.unjuanable.domain.orgmng.org.OrgRepository;
import demo.unjuanable.domain.orgmng.org.OrgStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommonOrgValidatorTest {

    public static final Long TENANT_ID = 1L;
    public static final Long ORG_ID = 1L;
    @Mock
    private OrgRepository orgRepository;

    @InjectMocks
    private CommonOrgValidator commonOrgValidator;

    @Test
    void shouldValid_shouldThrowException_whenOrgNotExist() {
        // Given
        doReturn(false)
                .when(orgRepository).existsByIdAndStatus(TENANT_ID, ORG_ID, OrgStatus.EFFECTIVE);

        // When
        BusinessException actualException = assertThrows(BusinessException.class
                , () -> commonOrgValidator.shouldValid(TENANT_ID, ORG_ID));

        // Then
        assertEquals("id为'1'的组织不是有效组织！", actualException.getMessage());
    }

    @Test
    void shouldValid_shouldPass_whenOrgExist() {

        doReturn(true)
                .when(orgRepository).existsByIdAndStatus(TENANT_ID, ORG_ID, OrgStatus.EFFECTIVE);

        // When
        commonOrgValidator.shouldValid(TENANT_ID, ORG_ID);

        // Then
        verify(orgRepository).existsByIdAndStatus(TENANT_ID, ORG_ID, OrgStatus.EFFECTIVE);

    }


}