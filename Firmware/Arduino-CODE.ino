#include <DHT.h>

#define DHTPIN 2    
#define DHTTYPE DHT11 
#define rele 8

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(rele, OUTPUT);

  Serial.begin(9600);

  dht.begin();
}

 //------------------------------------------------

void loop() {
  delay(2000);

  float h = dht.readHumidity();
  float t = dht.readTemperature();

 //------------------------------------------------

  if (isnan(h) || isnan(t)) {
    Serial.println("Erro ao ler do sensor DHT!");
    return;
  }

  //------------------------------------------------

  Serial.print(t);
  Serial.print(",");
  Serial.println(h);

 //------------------------------------------------
 //LED vermelho (alta temperatura)
  if (t >= 28){
  digitalWrite(rele, LOW);
  digitalWrite(10, HIGH);
  tone(3, 500);

  delay(1000);

  digitalWrite(rele, LOW);
  digitalWrite(10, LOW);
  noTone(3);

  delay(1000);
  }

//LED amarelo (alto nivel de umidade)
  else if (h >= 60){

  digitalWrite(rele, LOW);
  digitalWrite(11, HIGH);
  tone(3, 500);

  delay(1000);

  digitalWrite(11, LOW);
  noTone(3);

  delay(1000);

  }
  //LED verde (se estiver tudo certo)
  else {
    digitalWrite(9, HIGH);
    delay(1000);
    digitalWrite(9, LOW);
    digitalWrite(rele, HIGH);
  }

}