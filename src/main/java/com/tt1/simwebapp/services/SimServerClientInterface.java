package com.tt1.simwebapp.services;

import com.tt1.simwebapp.model.*;

/**
 * Define el contrato del cliente que provee de datos a la WebApp.
 */
public interface SimServerClientInterface {

    /**
     * Devuelve una lista con los nombres de las criaturas. Si no tiene problemas para conseguir la lista de criaturas
     * añade null como detalles de problema. En caso contrario, la lista de criaturas es null y los detalles de problema
     * contienen una cadena explicando el problema encontrado.
     *
     * @return la respuesta de la lista de criaturas.
     */
    CreaturesResponse getCreatures();

    /**
     * Devuelve cierto si el nombre está en la lista de criaturas obtenida por
     * {@link SimServerClientInterface#getCreatures()}, falso en caso contrario.
     *
     * @param name el nombre de la criatura a comprobar si es válido.
     * @return cierto si el nombre es válido y falso en caso contrario.
     */
    boolean isCreatureNameValid(String name);

    /**
     * Realiza una petición de simulación y devuelve un mensaje que contiene el token de referencia de la simulación
     * creada. Si no tiene problemas para realizar la petición u obtener el token añade null como detalles de problema.
     * En caso contrario, el token es null y los detalles de problema contienen una cadena explicando el problema
     * encontrado.
     *
     * @param simulationRequest la petición de simulación.
     * @return la respuesta de la petición de simulación.
     */
    SimulationRequestResponse requestSimulation(SimulationRequest simulationRequest);

    /**
     * Obtiene el estado de una simulación. Si no tiene problemas para obtener el estado añade null como detalles de
     * problema. En caso contrario, el estado de simulación es null y los detalles de problema contienen una cadena
     * explicando el problema encontrado.
     *
     * @param simulation la simulación a obtener el estado.
     * @return la respuesta del estado de simulación.
     */
    SimulationStatusResponse getSimulationStatus(Simulation simulation);

    /**
     * Obtiene una lista con las simulaciones de un usuario. Si no tiene problemas para obtener las simulaciones añade
     * null como detalles de problema. En caso contrario, las simulaciones son null y los detalles de problema contienen
     * una cadena explicando el problema encontrado.
     *
     * @param user el usuario a obtener sus simulaciones.
     * @return la respuesta de la lista de simulaciones.
     */
    UserSimulationsResponse getUserSimulations(User user);

    /**
     * Obtiene el resultado de una simulación. Si no tiene problemas para obtener el resultado añade null como detalles
     * de problema. En caso contrario, el resultado de simulación es null y los detalles de problema contienen una
     * cadena explicando el problema encontrado.
     *
     * @param simulation la simulación a obtener el resultado.
     * @return la respuesta del resultado de simulación.
     */
    SimulationResultResponse getSimulationResult(Simulation simulation);

    /**
     * Envía un email y obtiene el éxito de la respuesta. Si no tiene problemas para enviar el email añade null como
     * detalles de problema. En caso contrario, el éxito de enviar email es null y los detalles de problema contienen
     * una cadena explicando el problema encontrado.
     *
     * @param email el email a enviar.
     * @return la respuesta de éxito.
     */
    EmailResponse sendEmail(Email email);
}