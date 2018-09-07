package ru.stqa.pft.soap;

import com.buzzbuzhome.BBHLocation;
import com.buzzbuzhome.GeoIP;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp(){
    BBHLocation userLocation = new GeoIP().getGeoIPSoap12().getUserLocation("46.35.224.207");
    assertEquals(userLocation.getCountryCode(), "US");
  }
}
