package com.kader.guidomia_challenge.use_case


import com.kader.guidomia_challenge.common.Resource
import com.kader.guidomia_challenge.doamin.model.Car
import com.kader.guidomia_challenge.doamin.repository.CarRepository
import com.kader.guidomia_challenge.doamin.use_case.GetAllCarsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAllCarsUseCaseTest {

    @Mock
    lateinit var carRepository: CarRepository

    @Test
    fun `invoke with empty parameters calls getAllCars`(): Unit = runBlocking {
        // Given
        val getAllCarsUseCase = GetAllCarsUseCase(carRepository)
        val expectedCars = listOf(
            Car(
                consList = listOf(
                    "Bad direction"
                ),
                make = "Land Rover",
                marketPrice = 125000.0,
                model = "Range Rover",
                prosList = listOf(
                    "You can go everywhere",
                    "Good sound system"
                ),
                rating = 3,
                expanded = false,
                image = null
            ),
            Car(
                consList = listOf(
                    "Bad direction"
                ),
                make = "Roadster",
                marketPrice = 125000.0,
                model = "Alpine",
                prosList = listOf(
                    "You can go everywhere",
                    "Good sound system"
                ),
                rating = 3,
                expanded = false,
                image = null
            ),
        )
        `when`(carRepository.getAllCars()).thenReturn(flowOf(Resource.Success(expectedCars)))

        // When
        val result = getAllCarsUseCase("", "")

        // Then
        assertEquals(expectedCars, result.first().data)
        verify(carRepository).getAllCars()
    }

    @Test
    fun `invoke with non-empty parameters calls getCarsByParameters`(): Unit = runBlocking {
        // Given
        val getCarsByParametersUseCase = GetAllCarsUseCase(carRepository)
        val expectedCars = listOf(
            Car(
                consList = listOf(
                    "Bad direction"
                ),
                make = "Roadster",
                marketPrice = 125000.0,
                model = "Alpine",
                prosList = listOf(
                    "You can go everywhere",
                    "Good sound system"
                ),
                rating = 3,
                expanded = false,
                image = null
            ),
        )
        `when`(
            carRepository.getCarsByParameters(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(flowOf(Resource.Success(expectedCars)))

        // When
        val result = getCarsByParametersUseCase("Roadster", "Alpine")

        // Then
        assertEquals(expectedCars, result.first().data)
        verify(carRepository).getCarsByParameters("Roadster", "Alpine")
    }
}
