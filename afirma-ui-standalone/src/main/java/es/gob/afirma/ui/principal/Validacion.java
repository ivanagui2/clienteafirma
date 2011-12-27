/*
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un applet de libre distribucion cuyo codigo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espana
 * Este fichero se distribuye bajo licencia GPL version 3 segun las
 * condiciones que figuran en el fichero 'licence' que se acompana.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aqui las condiciones expresadas alli.
 */
package es.gob.afirma.ui.principal;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.Caret;

import es.gob.afirma.ui.listeners.ElementDescriptionFocusListener;
import es.gob.afirma.ui.listeners.ElementDescriptionMouseListener;
import es.gob.afirma.ui.utils.ConfigureCaret;
import es.gob.afirma.ui.utils.CustomDialog;
import es.gob.afirma.ui.utils.ExtFilter;
import es.gob.afirma.ui.utils.GeneralConfig;
import es.gob.afirma.ui.utils.HelpUtils;
import es.gob.afirma.ui.utils.Messages;
import es.gob.afirma.ui.utils.RequestFocusListener;
import es.gob.afirma.ui.utils.SelectionDialog;
import es.gob.afirma.ui.utils.SignedFileManager;
import es.gob.afirma.ui.utils.Utils;
import es.gob.afirma.ui.visor.ui.VisorPanel;

/** Clase que muestra el panel de validacion VALIDe */
class Validacion extends JPanel {

    private static final long serialVersionUID = 1L;

    /** Construye el panel y todos sus componentes visuales. */
    public Validacion() {
        initComponents();
    }

    /** Pulsar boton examinar: Muestra una ventana para seleccinar un archivo.
     * Modifica el valor de la caja con el nombre del archivo seleccionado
     * @param campoFichero Campo en el que se escribe el nombre del fichero seleccionado */
    void browseSignActionPerformed(final JTextField campoFichero) {
        final File selectedFile =
            SelectionDialog.showFileOpenDialog(this,
                                               Messages.getString("Validacion.chooser.title"), (ExtFilter) SignedFileManager.getCommonSignedFileFilter()); //$NON-NLS-1$
        if (selectedFile != null) {
            campoFichero.setText(selectedFile.getAbsolutePath());
        }
    }

    /** Inicializacion de componentes */
    private void initComponents() {
        // Eliminamos el layout
        setLayout(new GridBagLayout());

        final GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.insets = new Insets(13, 13, 0, 13);

        // Componentes para la seleccion de fichero
        final JLabel browseSignLabel = new JLabel();
        browseSignLabel.setText(Messages.getString("Validacion.buscar")); //$NON-NLS-1$
        browseSignLabel.getAccessibleContext().setAccessibleDescription(Messages.getString("Validacion.buscar.description")); //$NON-NLS-1$
        Utils.setContrastColor(browseSignLabel);
        Utils.setFontBold(browseSignLabel);
        add(browseSignLabel, c);

        c.gridwidth = 1;
        c.insets = new Insets(0, 13, 0, 0);
        c.weightx = 1.0;
        c.gridy = 1;

        // Campo donde se guarda el nombre del fichero a firmar
        final JTextField signFileField = new JTextField();
        signFileField.setToolTipText(Messages.getString("Validacion.buscar.caja.description")); //$NON-NLS-1$
        signFileField.addMouseListener(new ElementDescriptionMouseListener(PrincipalGUI.bar,
                                                                           Messages.getString("Validacion.buscar.caja.description.status"))); //$NON-NLS-1$
        signFileField.addFocusListener(new ElementDescriptionFocusListener(PrincipalGUI.bar,
                                                                           Messages.getString("Validacion.buscar.caja.description.status"))); //$NON-NLS-1$
        signFileField.getAccessibleContext().setAccessibleName(browseSignLabel.getText() + " ALT + R."); //$NON-NLS-1$
        signFileField.getAccessibleContext().setAccessibleDescription(Messages.getString("Validacion.buscar.caja.description")); //$NON-NLS-1$
        signFileField.addAncestorListener(new RequestFocusListener(false));

        Utils.remarcar(signFileField);
        if (GeneralConfig.isBigCaret()) {
            final Caret caret = new ConfigureCaret();
            signFileField.setCaret(caret);
        }
        Utils.setFontBold(signFileField);
        add(signFileField, c);

        // Relacion entre etiqueta y campo de texto
        browseSignLabel.setLabelFor(signFileField);
        // Asignacion de mnemonico
        browseSignLabel.setDisplayedMnemonic(KeyEvent.VK_R);

        c.insets = new Insets(0, 10, 0, 13);
        c.weightx = 0.0;
        c.gridx = 1;

        final JPanel panelExaminar = new JPanel(new GridLayout(1, 1));
        // Boton examinar
        final JButton browseSignButton = new JButton();
        browseSignButton.setMnemonic(KeyEvent.VK_E);
        browseSignButton.setText(Messages.getString("PrincipalGUI.Examinar")); //$NON-NLS-1$
        browseSignButton.setToolTipText(Messages.getString("PrincipalGUI.Examinar.description")); //$NON-NLS-1$
        browseSignButton.addMouseListener(new ElementDescriptionMouseListener(PrincipalGUI.bar,
                                                                              Messages.getString("PrincipalGUI.Examinar.description.status"))); //$NON-NLS-1$
        browseSignButton.addFocusListener(new ElementDescriptionFocusListener(PrincipalGUI.bar,
                                                                              Messages.getString("PrincipalGUI.Examinar.description.status"))); //$NON-NLS-1$
        //browseSignButton.getAccessibleContext().setAccessibleName(Messages.getString("PrincipalGUI.Examinar") + " " + Messages.getString("PrincipalGUI.Examinar.description.status")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        browseSignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                browseSignActionPerformed(signFileField);
            }
        });
        Utils.remarcar(browseSignButton);
        Utils.setContrastColor(browseSignButton);
        Utils.setFontBold(browseSignButton);
        panelExaminar.add(browseSignButton);
        add(panelExaminar, c);

        c.gridwidth = 2;
        c.insets = new Insets(0, 13, 0, 13);
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 2;

        // Panel vacio para alinear el boton de aceptar en la parte de abajo de la pantalla
        final JPanel emptyPanel = new JPanel();
        add(emptyPanel, c);

        // Panel con los botones
        final JPanel panelBotones = new JPanel(new GridBagLayout());

        final GridBagConstraints cons = new GridBagConstraints();
        cons.anchor = GridBagConstraints.FIRST_LINE_START; // control de la orientacion de componentes al redimensionar
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 15;
        cons.gridx = 0;

        // Etiqueta para rellenar a la izquierda
        final JLabel label = new JLabel();
        panelBotones.add(label, cons);

        final JPanel panelFirmar = new JPanel(new GridLayout(1, 1));
        // Boton firmar
        final JButton checkSignButton = new JButton();
        checkSignButton.setMnemonic(KeyEvent.VK_V);
        checkSignButton.setText(Messages.getString("Validacion.btnValidar")); //$NON-NLS-1$
        checkSignButton.setToolTipText(Messages.getString("Validacion.btnValidar.description")); //$NON-NLS-1$
        //checkSignButton.getAccessibleContext().setAccessibleName(Messages.getString("Validacion.btnValidar") + " " + Messages.getString("Validacion.btnValidar.description.status"));  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
        checkSignButton.addMouseListener(new ElementDescriptionMouseListener(PrincipalGUI.bar,
                                                                             Messages.getString("Validacion.btnValidar.description.status"))); //$NON-NLS-1$
        checkSignButton.addFocusListener(new ElementDescriptionFocusListener(PrincipalGUI.bar,
                                                                             Messages.getString("Validacion.btnValidar.description.status"))); //$NON-NLS-1$
        checkSignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                validateActionPerformance(signFileField.getText());
            }
        });
        checkSignButton.getAccessibleContext().setAccessibleDescription(Messages.getString("Validacion.btnValidar.description")); // NOI18N //$NON-NLS-1$
        Utils.remarcar(checkSignButton);
        Utils.setContrastColor(checkSignButton);
        Utils.setFontBold(checkSignButton);

        cons.ipadx = 0;
        cons.gridx = 1;
        cons.weightx = 1.0;

        final JPanel buttonPanel = new JPanel();
        panelFirmar.add(checkSignButton);
        buttonPanel.add(panelFirmar, BorderLayout.CENTER);
        panelBotones.add(buttonPanel, cons);

        cons.ipadx = 15;
        cons.weightx = 0.0;
        cons.gridx = 2;

        final JPanel panelAyuda = new JPanel();
        // Boton ayuda
        final JButton botonAyuda = HelpUtils.helpButton("validacion"); //$NON-NLS-1$
        botonAyuda.setName("helpButton"); //$NON-NLS-1$
        panelAyuda.add(botonAyuda);
        panelBotones.add(panelAyuda, cons);

        c.insets = new Insets(13, 13, 13, 13);
        c.weighty = 0.0;
        c.weightx = 1.0;
        c.gridy = 3;

        add(panelBotones, c);
    }

    /** Muestra el di&aacute;logo con la informaci&oacute;n de validaci&oacute;n
     * de una firma.
     * @param signPath Ruta del fichero de firma. */
    void validateActionPerformance(final String signPath) {
        if (signPath == null || signPath.trim().length() <= 0) {
            CustomDialog.showMessageDialog(SwingUtilities.getRoot(this),
                                           true,
                                           Messages.getString("Validacion.msg.error.fichero"), Messages.getString("error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        }

        final File signFile = new File(signPath);
        if (!signFile.exists() || !signFile.isFile()) {
            CustomDialog.showMessageDialog(SwingUtilities.getRoot(this),
                                           true,
                                           Messages.getString("Validacion.msg.error.nofichero", signPath), Messages.getString("error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        }

        if (!signFile.canRead()) {
            CustomDialog.showMessageDialog(SwingUtilities.getRoot(this),
                                           true,
                                           Messages.getString("Validacion.msg.error.noLectura"), Messages.getString("error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        }

        final VisorPanel visorPanel = new VisorPanel(signFile, null);
        visorPanel.setTitle(Messages.getString("Visor.window.title")); //$NON-NLS-1$

        visorPanel.setVisible(true);
    }
}
