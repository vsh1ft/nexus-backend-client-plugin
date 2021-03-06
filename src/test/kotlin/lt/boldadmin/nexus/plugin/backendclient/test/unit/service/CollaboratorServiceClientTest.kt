package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.valueobject.time.DayMinuteInterval
import lt.boldadmin.nexus.api.type.valueobject.time.MinuteInterval
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.collaborator.CollaboratorServiceClient
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.DayOfWeek.TUESDAY

@ExtendWith(MockitoExtension::class)
class CollaboratorServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClient: CollaboratorServiceClient

    @BeforeEach
    fun setUp() {
        serviceClient = CollaboratorServiceClient(httpClientSpy)
    }

    @Test
    fun `Saves collaborator`() {
        val collaborator = Collaborator()

        serviceClient.save(collaborator)

        verify(httpClientSpy).post("/collaborator/save", collaborator)
    }

    @Test
    fun `Updates attribute`() {
        val attributeName = "attributeName"
        val attributeValue = "attributeValue"
        val collaboratorId = "collaboratorId"

        serviceClient.update(collaboratorId, attributeName, attributeValue)

        verify(httpClientSpy).post("/collaborator/$collaboratorId/attribute/$attributeName/update", attributeValue)
    }

    @Test
    fun `Updates work week`() {
        val collaboratorId = "uniqueCollaboratorId"
        val workWeek = sortedSetOf(DayMinuteInterval(TUESDAY, MinuteInterval(100, 200), false))

        serviceClient.update(collaboratorId, workWeek)

        verify(httpClientSpy).postJson("/collaborator/$collaboratorId/work-week/update", workWeek)
    }

    @Test
    fun `Updates order number`() {
        val orderNumber: Short = 5
        val collaboratorId = "collaboratorId"

        serviceClient.updateOrderNumber(collaboratorId, orderNumber)

        verify(httpClientSpy).post("/collaborator/$collaboratorId/attribute/order-number/update", orderNumber)
    }

    @Test
    fun `Creates collaborator with defaults`() {
        val expectedCollaborator = Collaborator()
        doReturn(expectedCollaborator)
            .`when`(httpClientSpy)
            .get("/collaborator/create-with-defaults", Collaborator::class.java)

        val actualCollaborator = serviceClient.createWithDefaults()

        assertSame(expectedCollaborator, actualCollaborator)
    }

    @Test
    fun `Gets collaborator by id`() {
        val expectedCollaborator = Collaborator()
        val collaboratorId = "collaboratorId"
        doReturn(expectedCollaborator)
            .`when`(httpClientSpy).get("/collaborator/$collaboratorId", Collaborator::class.java)

        val actualCollaborator = serviceClient.getById(collaboratorId)

        assertSame(expectedCollaborator, actualCollaborator)
    }

    @Test
    fun `Gets collaborator by mobile number`() {
        val expectedCollaborator = Collaborator()
        val mobileNumber = "mobileNumber"
        doReturn(expectedCollaborator)
            .`when`(httpClientSpy)
            .get("/collaborator/mobile-number/$mobileNumber", Collaborator::class.java)

        val actualCollaborator = serviceClient.getByMobileNumber(mobileNumber)

        assertSame(expectedCollaborator, actualCollaborator)
    }

    @Test
    fun `Exists collaborator by mobile number`() {
        val mobileNumber = "mobileNumber"
        doReturn(true)
            .`when`(httpClientSpy)
            .get("/collaborator/mobile-number/$mobileNumber/exists", Boolean::class.java)

        val exists = serviceClient.existsByMobileNumber(mobileNumber)

        assertTrue(exists)
    }

    @Test
    fun `Exists collaborator by id`() {
        val collaboratorId = "collaboratorId"
        doReturn(true)
            .`when`(httpClientSpy)
            .get("/collaborator/$collaboratorId/exists", Boolean::class.java)

        val exists = serviceClient.existsById(collaboratorId)

        assertTrue(exists)
    }
}
