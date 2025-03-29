# 💸 ExamenCajeroAutomatico

Este proyecto es una solución práctica al ejercicio de simulación de un **cajero automático**. Fue desarrollado con **Spring Boot 3.3.0**, **Java 17**, **Oracle**, **JPA** y **Thymeleaf**, proporcionando una interfaz web sencilla y funcional para realizar retiros de efectivo de un cajero virtual.

---

## 🎯 Objetivo

Simular el funcionamiento básico de un cajero automático que:

- Inicia el día con un monto fijo de **$12,550 MXN**
- Contiene distintas **denominaciones de billetes y monedas**
- Permite realizar **retiros de efectivo**
- No admite depósitos ni recargas de saldo

El cajero debe entregar el monto solicitado en las **denominaciones disponibles**, calculando automáticamente la combinación adecuada.

---

## 🏦 Denominaciones disponibles

| Tipo    | Cantidad | Denominación |
|---------|----------|--------------|
| Billete | 2        | $1000        |
| Billete | 5        | $500         |
| Billete | 10       | $200         |
| Billete | 20       | $100         |
| Billete | 30       | $50          |
| Billete | 40       | $20          |
| Moneda  | 50       | $10          |
| Moneda  | 100      | $5           |
| Moneda  | 200      | $2           |
| Moneda  | 300      | $1           |
| Moneda  | 100      | $0.50        |

---

## 🖥️ Interfaz del usuario

El usuario puede:
- Ingresar el monto a retirar en una interfaz web amigable (`/atm/home`)
- Ver las denominaciones disponibles (`/atm/denominaciones`)
- Visualizar el resultado del retiro, incluyendo los billetes y monedas entregados, y el efectivo restante

La interfaz está construida con **Thymeleaf** y **Bootstrap 5**.

---

## 🧰 Tecnologías utilizadas

- **Backend**: Spring Boot 3.3.0, Java 17, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, HTML5
- **Base de datos**: Oracle 19c
- **ORM**: Hibernate
- **IDE**: IntelliJ IDEA

---

## 🗂️ Estructura del proyecto

- `controller`: Controladores para manejar rutas (`/atm/home`, `/atm/retirar`, `/atm/denominaciones`)
- `model`: Entidad `Cajero` que representa cada denominación
- `repository`: `CajeroRepository` para acceso a base de datos
- `service`: `CajeroService` que contiene la lógica de negocio (retiros, cálculo de total, validaciones)
- `resources/templates`: Vistas HTML (index, resultado, denominaciones)

---

## 🚀 Cómo ejecutar el proyecto

### Prerrequisitos

- Java 17
- Maven
- Oracle DB (o adaptarlo a H2 si lo deseas probar rápido)

### Pasos

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/ExamenCajeroAutomatico.git
   cd ExamenCajeroAutomatico
