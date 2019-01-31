package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.ProjectServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame

@RunWith(MockitoJUnitRunner::class)
class ProjectServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var projectServiceClientSpy: ProjectServiceClient

    @Before
    fun setUp() {
        projectServiceClientSpy = ProjectServiceClient(httpClientSpy)
    }

    @Test
    fun `Updates attribute`() {
        val attributeName = "attributeName"
        val attributeValue = "attributeValue"
        val projectId = "projectId"

        projectServiceClientSpy.update(projectId, attributeName, attributeValue)

        verify(httpClientSpy).post("/project/$projectId/attribute/$attributeName/update", attributeValue)
    }

    @Test
    fun `Updates order number`() {
        val orderNumber: Short = 5
        val projectId = "projectId"

        projectServiceClientSpy.updateOrderNumber(projectId, orderNumber)

        verify(httpClientSpy).post("/project/$projectId/attribute/order-number/update", orderNumber)
    }

    @Test
    fun `Creates project with defaults`() {
        val expectedProject = Project()
        val userId = "userId"
        doReturn(expectedProject).`when`(httpClientSpy).get("/project/user/$userId/create-with-defaults", Project::class.java)

        val actualProject = projectServiceClientSpy.createWithDefaults(userId)

        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Gets project by id`() {
        val expectedProject = Project()
        val projectId = "projectId"
        doReturn(expectedProject).`when`(httpClientSpy).get("/project/$projectId", Project::class.java)

        val actualProject = projectServiceClientSpy.getById(projectId)

        assertSame(expectedProject, actualProject)
    }
}