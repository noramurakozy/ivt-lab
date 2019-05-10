package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTorpedoStore1;
  private TorpedoStore mockTorpedoStore2;

  @BeforeEach
  public void init(){
    mockTorpedoStore1 = mock(TorpedoStore.class);
    mockTorpedoStore2 = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTorpedoStore1, mockTorpedoStore2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockTorpedoStore1.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTorpedoStore1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockTorpedoStore1.fire(1)).thenReturn(true);
    when(mockTorpedoStore2.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTorpedoStore1, atLeastOnce()).fire(1);
    verify(mockTorpedoStore2, atLeastOnce()).fire(1);
  }

  @Test
  public void fireTorpedo_FirstTimePrimaryTorpedo(){
      //Arrange
      when(mockTorpedoStore1.fire(1)).thenReturn(true);
      when(mockTorpedoStore2.fire(1)).thenReturn(false);
      //Act
      ship.fireTorpedo(FiringMode.SINGLE);
      //Assert
      verify(mockTorpedoStore1, times(1)).fire(1);
      verify(mockTorpedoStore2, never()).fire(1);
  }

  @Test
  public void fireTorpedo_TrySecondaryTorpedoIfPrimaryAlreadyFired(){
      //Arrange
      when(mockTorpedoStore1.fire(1)).thenReturn(true);
      when(mockTorpedoStore2.fire(1)).thenReturn(true);
      //Act
      ship.fireTorpedo(FiringMode.SINGLE);
      ship.fireTorpedo(FiringMode.SINGLE);
      //Assert
      verify(mockTorpedoStore1, times(1)).fire(1);
      verify(mockTorpedoStore2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_TryPrimaryTorpedoIfSecondaryAlreadyFired(){
      //Arrange
      when(mockTorpedoStore1.fire(1)).thenReturn(true);
      when(mockTorpedoStore2.fire(1)).thenReturn(true);
      //Act
      ship.fireTorpedo(FiringMode.SINGLE);
      ship.fireTorpedo(FiringMode.SINGLE);
      //Assert
      verify(mockTorpedoStore1, times(1)).fire(1);
      verify(mockTorpedoStore2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_TryPrimaryIfSecondaryIsEmpty(){
      //Arrange
      when(mockTorpedoStore2.isEmpty()).thenReturn(true);
      //Act
      ship.fireTorpedo(FiringMode.SINGLE);
      //Assert
      verify(mockTorpedoStore1, times(1)).fire(1);
      verify(mockTorpedoStore2, never()).fire(1);
  }

  @Test
  public void fireTorpedo_BothTorpedoEmpty(){
      //Arrange
      when(mockTorpedoStore1.isEmpty()).thenReturn(true);
      when(mockTorpedoStore2.isEmpty()).thenReturn(true);
      //Act
      ship.fireTorpedo(FiringMode.SINGLE);
      //Assert
      verify(mockTorpedoStore1, never()).fire(1);
      verify(mockTorpedoStore2, never()).fire(1);
  }

  /*@Test
  public void fireTorpedo_BothStoreBecomeEmpty(){
      //Arrange
      when(mockTorpedoStore1.isEmpty()).thenReturn(true);
      when(mockTorpedoStore2.isEmpty()).thenReturn(false);
      //Act
      ship.fireTorpedo(FiringMode.SINGLE);
      //Assert
      mockTorpedoStore1.isEmpty().eq(true);
      mockTorpedoStore2.isEmpty().eq(true);
  }*/

}
