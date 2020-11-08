import React from 'react';
import styles from './index.module.scss';

export default function Footer() {
  return (
    <p className={styles.footer}>
      <span className={styles.logo}>Amber</span>
      <br />
      <span className={styles.copyright}>© 2020.11-NOW Coastline</span>
    </p>
  );
}
