package jp.co.yumemi.android.code_check.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.junit.MockitoJUnitRunner

/**
 * Test class for the NetworkUtils utility class.
 */
@RunWith(MockitoJUnitRunner::class)
class NetworkUtilsTest {

    /**
     * Mock ConnectivityManager used for testing network-related functionality.
     */
    @Mock
    private lateinit var mockConnectivityManager: ConnectivityManager

    /**
     * Mock NetworkCapabilities used for testing network-related functionality.
     */
    @Mock
    private lateinit var mockNetworkCapabilities: NetworkCapabilities

    /**
     * Set up method to initialize Mockito mocks.
     */
    @Before
    fun setup() {
        openMocks(this)
    }

    /**
     * Test case to verify behavior when the ConnectivityManager is null.
     */
    @Test
    fun `test isNetworkAvailable with null ConnectivityManager`() {
        assertFalse(NetworkUtils.isNetworkAvailable())
    }

    /**
     * Test case to verify behavior when the active network is null.
     */
    @Test
    fun `test isNetworkAvailable with null active network`() {
        `when`(mockConnectivityManager.activeNetwork)
            .thenReturn(null)
        NetworkUtils.init(mockConnectivityManager)
        assertFalse(NetworkUtils.isNetworkAvailable())
    }

    /**
     * Test case to verify behavior when the active network has cellular transport.
     */
    @Test
    fun `test isNetworkAvailable with cellular transport`() {
        `when`(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            .thenReturn(true)
        `when`(mockConnectivityManager.getNetworkCapabilities(mockConnectivityManager.activeNetwork))
            .thenReturn(mockNetworkCapabilities)
        NetworkUtils.init(mockConnectivityManager)
        assertTrue(NetworkUtils.isNetworkAvailable())
    }

    /**
     * Test case to verify behavior when the active network has WiFi transport.
     */
    @Test
    fun `test isNetworkAvailable with wifi transport`() {
        `when`(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(true)
        `when`(mockConnectivityManager.getNetworkCapabilities(mockConnectivityManager.activeNetwork))
            .thenReturn(mockNetworkCapabilities)
        NetworkUtils.init(mockConnectivityManager)
        assertTrue(NetworkUtils.isNetworkAvailable())
    }

    /**
     * Test case to verify behavior when the active network has Ethernet transport.
     */
    @Test
    fun `test isNetworkAvailable with ethernet transport`() {
        `when`(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
            .thenReturn(true)
        `when`(mockConnectivityManager.getNetworkCapabilities(mockConnectivityManager.activeNetwork))
            .thenReturn(mockNetworkCapabilities)
        NetworkUtils.init(mockConnectivityManager)
        assertTrue(NetworkUtils.isNetworkAvailable())
    }

    /**
     * Test case to verify behavior when the active network has no valid transports.
     */
    @Test
    fun `test isNetworkAvailable with no valid transports`() {
        `when`(mockConnectivityManager.getNetworkCapabilities(mockConnectivityManager.activeNetwork))
            .thenReturn(mockNetworkCapabilities)
        NetworkUtils.init(mockConnectivityManager)
        assertFalse(NetworkUtils.isNetworkAvailable())
    }
}
