package com.tt1.simwebapp.logica;

import com.tt1.simwebapp.modelo.Destinatario;

/**
 * Gestor de las comunicaciones con el servidor de email.
 */
public interface InterfazEnviarEmails
{
    /**
     * Realiza una solicitud de envío de email al servidor de emails con el contenido y destinatario pasados como
     * parámetros. Devuelve cierto si el email se envía con éxito. En caso contrario, devuelve falso.
     *
     * @param dest    el destinatario del email.
     * @param message el mensaje del email.
     * @return cierto si el email se envía con éxito, falso en caso contrario.
     */
    boolean enviarEmail(Destinatario dest, String message);
}
