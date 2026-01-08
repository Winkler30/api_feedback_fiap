const express = require('express');
const sql = require('mssql');

const app = express();

app.use(express.json());

const dbConfig = {
  user: 'azureDBFiapProject',
  password: 'FkjhnaolfUJb%623@4wsd',
  server: 'fiap-db-server.database.windows.net',
  database: 'fiap-db-server',
  options: {
    encrypt: true
  }
};

app.post('/avaliacao', async (req, res) => {
  const { codAluno, codAula, nota, texto } = req.body || {};

  if (codAluno === undefined || codAula === undefined || nota === undefined) {
    return res.status(400).json({
      erro: 'codAluno, codAula e nota são obrigatórios'
    });
  }

  try {
    const pool = await sql.connect(dbConfig);

    await pool.request()
      .input('codAula', sql.Int, Number(codAula))
      .input('nota', sql.Int, Number(nota))
      .input('texto', sql.Text, texto || null)
      .input('codAluno', sql.Int, Number(codAluno))
      .query(`
        INSERT INTO AVALIACAO_AULA
        (COD_ID_AULA, NOTA_AVALIACAO, TEXTO_AVALIACAO, COD_ID_ALUNO)
        VALUES (@codAula, @nota, @texto, @codAluno)
      `);

    res.status(201).json({
      mensagem: 'Avaliação salva com sucesso'
    });

  } catch (err) {
    console.error(err);
    res.status(500).json({
      erro: 'Erro ao salvar avaliação',
      detalhe: err.message
    });
  }
});

app.listen(3000, () => {
  console.log('API rodando em http://localhost:3000');
});
