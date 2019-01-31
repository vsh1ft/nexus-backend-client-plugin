package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.User
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.UserServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class UserServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var userServiceClient: UserServiceClient

    @Before
    fun setUp() {
        userServiceClient = UserServiceClient(httpClientSpy)
    }

    @Test
    fun `Saves user`() {
        val user = User()

        userServiceClient.save(user)

        verify(httpClientSpy).postJson("/user/save", user)
    }

    @Test
    fun `Exists any user`() {
        doReturn(true).`when`(httpClientSpy).get("/user/exists-any", Boolean::class.java)

        val exists = userServiceClient.existsAny()

        assertTrue(exists)
    }

    @Test
    fun `Creates with defaults`() {
        val expectedUser = User()
        doReturn(expectedUser).`when`(httpClientSpy).get("/user/create-with-defaults", User::class.java)

        val actualUser = userServiceClient.createWithDefaults()

        assertSame(expectedUser, actualUser)
    }

    @Test
    fun `Gets user by id`() {
        val expectedUser = User()
        val userId = "userId"
        doReturn(expectedUser).`when`(httpClientSpy).get("/user/$userId", User::class.java)

        val actualUser = userServiceClient.getById(userId)

        assertSame(expectedUser, actualUser)
    }

    @Test
    fun `Exists by email`() {
        val email = "email"
        doReturn(true).`when`(httpClientSpy).get("/user/email/$email/exists", Boolean::class.java)

        val exists = userServiceClient.existsByEmail(email)

        assertTrue(exists)
    }

    @Test
    fun `Gets user by project id`() {
        val expectedUser = User()
        val projectId = "projectId"
        doReturn(expectedUser).`when`(httpClientSpy).get("/user/project/$projectId", User::class.java)

        val actualUser = userServiceClient.getByProjectId(projectId)

        assertSame(expectedUser, actualUser)
    }

    @Test
    fun `Gets user by email`() {
        val expectedUser = User()
        val email = "email"
        doReturn(expectedUser).`when`(httpClientSpy).get("/user/email/$email", User::class.java)

        val actualUser = userServiceClient.getByEmail(email)

        assertSame(expectedUser, actualUser)
    }

    @Test
    fun `User has customer`() {
        val userId = "userId"
        val customerId = "customerId"
        doReturn(true).`when`(httpClientSpy).get("/user/$userId/customer/$customerId/has-customer", Boolean::class.java)

        val hasCustomer = userServiceClient.doesUserHaveCustomer(userId, customerId)

        assertTrue(hasCustomer)
    }

    @Test
    fun `User has project`() {
        val userId = "userId"
        val projectId = "projectId"
        doReturn(true).`when`(httpClientSpy).get("/user/$userId/project/$projectId/has-project", Boolean::class.java)

        val hasProject = userServiceClient.doesUserHaveProject(userId, projectId)

        assertTrue(hasProject)
    }

    @Test
    fun `User has collaborator`() {
        val userId = "userId"
        val collaboratorId = "collaboratorId"
        doReturn(true).`when`(httpClientSpy).get("/user/$userId/collaborator/$collaboratorId/has-collaborator", Boolean::class.java)

        val hasCollaborator = userServiceClient.doesUserHaveCollaborator(userId, collaboratorId)

        assertTrue(hasCollaborator)
    }

    @Test
    fun `Project name is unique`() {
        val userId = "userId"
        val projectId = "projectId"
        val projectName = "projectName"
        doReturn(true).`when`(httpClientSpy).get("/user/$userId/project/$projectId/name/$projectName/is-unique", Boolean::class.java)

        val isUnique = userServiceClient.isProjectNameUnique(projectName, projectId, userId)

        assertTrue(isUnique)
    }
}