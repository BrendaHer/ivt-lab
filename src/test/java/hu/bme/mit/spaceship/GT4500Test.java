package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {
  private TorpedoStore mockStore1;
  private TorpedoStore mockStore2;
  private GT4500 ship;

  @BeforeEach
  public void init(){
    mockStore1 = mock(TorpedoStore.class);
    mockStore2 = mock(TorpedoStore.class);
    this.ship = new GT4500();
        this.ship.injectDependencies(mockStore1, mockStore2, false);

  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockStore1.fire(1)).thenReturn(true);
    when(mockStore2.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockStore1, times(1)).fire(1);
    verify(mockStore2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockStore1.fire(1)).thenReturn(true);
    when(mockStore2.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockStore1, times(1)).fire(1);
    verify(mockStore2, times(1)).fire(1);
  }
//-------------------------------------NEW---------------------------------------------------------
    @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when(mockStore1.fire(1)).thenReturn(false);
    when(mockStore2.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);;
    // Assert
    assertEquals(false, result);
    verify(mockStore1, times(1)).fire(1);
    verify(mockStore2, times(0)).fire(1);
  }
    @Test
  public void fireTorpedo_All_Failure(){
    // Arrange
    when(mockStore1.fire(1)).thenReturn(false);
    when(mockStore2.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockStore1, times(1)).fire(1);
    verify(mockStore2, times(1)).fire(1);
  }

@Test
  public void fireTorpedo_FiresOne_ThenEmpty_Failure(){
    // Arrange

    when(mockStore1.fire(1)).thenReturn(true);
    when(mockStore2.isEmpty()).thenReturn(false);
    // Act
    boolean result_first_try = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result_second_try = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, result_first_try);
    assertEquals(false, result_second_try);
    verify(mockStore1, times(1)).fire(1);
    verify(mockStore1, times(1)).isEmpty();
  }

    @Test
  public void fireTorpedo_SINGLE_PrimaryAndSecondaryEmpty() {
    // Arrange
    when(mockStore1.isEmpty()).thenReturn(true);
    when(mockStore1.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    verify(mockStore1, times(1)).isEmpty();
    verify(mockStore1, times(0)).fire(1);

    verify(mockStore1, times(1)).isEmpty();
    verify(mockStore1, times(0)).fire(1); 
    assertEquals(false, result);   
  }

  @Test
  public void fireTorpedo_ALL_BothFailure() {
    // Arrange
    when(mockStore1.isEmpty()).thenReturn(false);
    when(mockStore1.fire(1)).thenReturn(false);

    when(mockStore1.isEmpty()).thenReturn(false);
    when(mockStore1.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockStore1, times(1)).isEmpty();
    verify(mockStore1, times(1)).fire(1);

    verify(mockStore1, times(1)).isEmpty();
    verify(mockStore1, times(1)).fire(1);
    assertEquals(false, result);
  }

}
