package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.status.location

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.location.WorklogLocationServiceClient
import org.junit.jupiter.api.Test

class WorklogLocationServiceClientTest {

    @Test
    fun `Logs work by location`() {
        val httpClientSpy: BackendHttpClient = mock()
        val collaborator = Collaborator()
        val coordinates = Coordinates(123.0, 123.0)

        WorklogLocationServiceClient(httpClientSpy).logWork(collaborator, coordinates)

        verify(httpClientSpy).postAsJson("/worklog/status/log-work/location", Pair(collaborator, coordinates))
    }
}