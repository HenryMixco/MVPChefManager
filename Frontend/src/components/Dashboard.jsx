import React from 'react';
import { Container } from 'react-bootstrap';
import Navigation from './Navigation';

function Dashboard() {
    return (
        <>
            <Navigation />
            <Container fluid className="main-content">
                <h1 className="section-title">Dashboard</h1>
                <div className="card">
                    <div className="card-body text-center" style={{ padding: '4rem' }}>
                        <h3 style={{ color: '#a0a0a0' }}>
                            Dashboard en construcción
                        </h3>
                        <p style={{ color: '#6c757d', marginTop: '1rem' }}>
                            Esta sección estará disponible próximamente
                        </p>
                    </div>
                </div>
            </Container>
        </>
    );
}

export default Dashboard;