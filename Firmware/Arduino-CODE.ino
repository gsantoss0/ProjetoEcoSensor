#include <DHT.h>

#define DHTPIN 2    
#define DHTTYPE DHT11 

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  pinMode(11, OUTPUT);

  Serial.begin(9600);

  dht.begin(); 
}

void loop() {
  delay(2000); 

  float h = dht.readHumidity();    
  float t = dht.readTemperature(); 


  if (isnan(h) || isnan(t)) {
    Serial.println("Erro ao ler do sensor DHT!");
    return;
  }

  Serial.print("Umidade: ");
  Serial.print(h);
  Serial.print("%  ");
  Serial.print("Temperatura: ");
  Serial.print(t);
  Serial.println("°C");

  if (t >= 28)
  {
  Serial.println("ALERTA... Alta temperatura!");
  digitalWrite(11, HIGH);
  delay(1000);
  digitalWrite(11, LOW);
  delay(1000);
  }

  if (h <= 30 || h >= 60)
  {
  Serial.println("ALERTA... Alto ou baixo nivel de humidade!");
  digitalWrite(11, HIGH);
  delay(1000);
  digitalWrite(11, LOW);
  delay(1000);
  }
}
